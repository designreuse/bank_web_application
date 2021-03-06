package com.bank.mvc.dao.hibernate;

import com.bank.mvc.dao.CreditDao;
import com.bank.mvc.models.Credit;
import com.bank.mvc.models.enums.ListStatus;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Created by Zalman on 19.05.2015.
 */
@Repository
@Transactional
public class HibernateCreditDao implements CreditDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public Collection<Credit> getAll() {
        return sessionFactory.getCurrentSession().
                createQuery("from Credit").list();
    }

    @Override
    public Collection<Credit> getByUserId(long userId) {
        return sessionFactory.getCurrentSession().
                createQuery("select c from Credit as c inner join c.account as ac where ac.user.id=:userId ").setParameter("userId", userId).list();
    }

    @Override
    public Collection<Credit> getAllUnconfirmed() {
        return sessionFactory.getCurrentSession().
                createQuery("from Credit c where c.listStatus=:status").setParameter("status", ListStatus.UNCONFIRMED).list();
    }

    @Override
    public Credit getById(long creditId) {
        return (Credit)sessionFactory.getCurrentSession()
                .get(Credit.class, creditId);
    }

    @Override
    public void save(Credit credit) {
        sessionFactory.getCurrentSession().merge(credit);
    }

    @Override
    public void delete(Credit credit) {
        sessionFactory.getCurrentSession().delete(credit);
    }
}
