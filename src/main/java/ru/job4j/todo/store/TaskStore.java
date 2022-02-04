package ru.job4j.todo.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todo.model.Task;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TaskStore implements AutoCloseable {

    private static final Logger LOGGER = Logger.getLogger(TaskStore.class.getName());

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

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
        try {
            Session session = sf.openSession();
            List result = session.createQuery("from ru.job4j.todo.model.Task").list();
            session.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
        try {
            Session session = sf.openSession();
            List result = session
                    .createQuery("from ru.job4j.todo.model.Task where done = false").list();
            session.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    public static void main(String[] args) {
        TaskStore store = new TaskStore();
        store.add(new Task()
                .setDescription("s")
                .setCreated(new Timestamp(System.currentTimeMillis()))
                .setDone(false));

        System.out.println(store.findAll());
    }
}
