package com.bank.mvc.dao.hibernate;

import com.bank.mvc.dao.UserDao;
import com.bank.mvc.models.User;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Repository
@Transactional
public class HibernateUserDao implements UserDao {

    @Autowired
    SessionFactory sessionFactory;

    //@Transactional(readOnly = true)
    @Override
    public Collection<User> getAll() {
        String hql = "from User";
        return sessionFactory.getCurrentSession().
                createQuery("from User").list();
    }

    //@Transactional(readOnly = true)
    @Override
    public User getById(int userId) {
        return (User)sessionFactory.getCurrentSession()
                .get(User.class, userId);
    }

    @Override
    public User getByUsername(String username) {

        Query query = sessionFactory.getCurrentSession().createQuery("from User u where u.username=:username");
        query.setParameter("username", username);

        User user = (User) query.uniqueResult();

        return user;
    }

    @Override
    public void save(User user) {
        sessionFactory.getCurrentSession().merge(user);
    }

    @Override
    public void delete(User user) {
        sessionFactory.getCurrentSession().delete(user);
    }


}