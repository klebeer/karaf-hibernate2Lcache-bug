package org.ecuadorjug.jpa.impl;

import org.apache.aries.jpa.template.JpaTemplate;
import org.apache.aries.jpa.template.TransactionType;
import org.ecuadorjug.jpa.User;
import org.ecuadorjug.jpa.UserService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;


@Component(service = UserService.class, immediate = true)
public class UserServiceImpl implements UserService {

    @Reference(target = "(osgi.unit.name=user-hibernate)")
    private JpaTemplate jpaTemplate;

    @Override
    public void add(User user) {
        jpaTemplate.tx(TransactionType.RequiresNew, entityManager -> {
            entityManager.persist(user);
            entityManager.flush();
        });
    }

    @Override
    public List<User> list() {
        return jpaTemplate.txExpr(TransactionType.Supports,
                entityManager -> entityManager.createQuery("SELECT b FROM User b", User.class).getResultList());
    }

    @Override
    public User get(Long id) {
        return jpaTemplate.txExpr(TransactionType.Supports,
                entityManager -> entityManager.find(User.class, id));
    }

    @Override
    public void remove(Long id) {
        jpaTemplate.tx(TransactionType.RequiresNew, entityManager -> {
            User user = entityManager.find(User.class, id);
            if (user != null) {
                entityManager.remove(user);
            }
        });
    }
}
