package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;

public class TaskStore implements AutoCloseable {

    private static final Logger LOGGER = Logger.getLogger(TaskStore.class.getName());

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private TaskStore() {

    }

    private static class Holder {
        private static final TaskStore INST = new TaskStore();
    }

    public static TaskStore getInstance() {
        return Holder.INST;
    }

    public Task add(Task task) {
        try {
            Session session = sf.openSession();
            session.beginTransaction();
            session.save(task);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return task;
    }

    public boolean replace(int id, Task task) {
        try {
            Session session = sf.openSession();
            session.beginTransaction();
            task.setId(id);
            session.update(task);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void update(int id) {

        try {
            Session session = sf.openSession();
            session.beginTransaction();

            String hql = "update ru.job4j.todo.model.Task SET done = :done where id = :id";
            Query query = session.createQuery(hql);

            query.setParameter("done", true);
            query.setParameter("id", id);
            query.executeUpdate();

            session.getTransaction().commit();
            session.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean delete(int id) {
        try {
            Session session = sf.openSession();
            session.beginTransaction();
            session.delete(findById(id));
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Task> findAll() {

        return this.tx(
                session -> session
                        .createQuery("select distinct t from Task t join fetch t.categories").list()
        );

    }

    public Task findById(int id) {
        try {
            Session session = sf.openSession();
            Task result = session.get(Task.class, id);
            session.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Collection<Task> findActiveTask() {

        return this.tx(
                session -> session
                        .createQuery("from ru.job4j.todo.model.Task where done = false").list()
        );

    }

    public User add(User user) {
        try {
            Session session = sf.openSession();
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public User findUserByEmail(String email) {

        try {
            Session session = sf.openSession();
            session.beginTransaction();
            Query query = session
                    .createQuery("from ru.job4j.todo.model.User where email = :email");
            query.setParameter("email", email);

            session.getTransaction().commit();
            User rsl = (User) query.uniqueResult();
            session.close();
            return rsl;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void addNewTask(Task task, String[] ids) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();

            for (String id : ids) {
                Category category = session.find(Category.class, Integer.parseInt(id));
                task.addCategory(category);
            }
            session.save(task);

            session.getTransaction().commit();
        } catch (Exception e) {
            sf.getCurrentSession().getTransaction().rollback();
        }
    }

    public List<Category> allCategories() {
        List<Category> rsl = new ArrayList<>();
        try (Session session = sf.openSession()) {
            session.beginTransaction();

            rsl = session.createQuery("select c from Category c", Category.class).list();

            session.getTransaction().commit();
        } catch (Exception e) {
            sf.getCurrentSession().getTransaction().rollback();
        }
        return rsl;
    }

}
