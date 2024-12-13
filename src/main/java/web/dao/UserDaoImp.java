package web.dao;

import web.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;


@Repository
public class UserDaoImp implements UserDao {

   @PersistenceContext
   private EntityManager entityManager;

   @Override
   public void add(User user) {
      entityManager.persist(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query = entityManager.createQuery("from User u left join fetch u.car", User.class);
      return query.getResultList();
   }

   @Override
   public User getUserByCar(int serial, String model) {
      String hql = "SELECT u FROM User u JOIN fetch u.car c WHERE c.model = :model AND c.serial = :series";
      TypedQuery<User> query = entityManager.createQuery(hql, User.class);
      query.setParameter("model", model);
      query.setParameter("series", serial);

      return query.getResultList().stream().findFirst().orElse(null);
   }
}
