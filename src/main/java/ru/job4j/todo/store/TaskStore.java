package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todo.model.Task;

import javax.persistence.Query;
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
        Task task = findById(id);
        try {
            Session session = sf.openSession();
            session.beginTransaction();

            String hql = "update ru.job4j.todo.model.Task SET done = :done where id = :id";
            Query query = session.createQuery(hql);

            query.setParameter("done", !task.isDone());
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
                session -> session.createQuery("from ru.job4j.todo.model.Task").list()
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

}
