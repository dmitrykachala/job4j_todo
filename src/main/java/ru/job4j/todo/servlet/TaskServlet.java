package ru.job4j.todo.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.store.TaskStore;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;

public class TaskServlet extends HttpServlet {
    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json; charset=utf-8");
        OutputStream output = resp.getOutputStream();
        TaskStore store = TaskStore.getInstance();
        String json = GSON.toJson(store.findAll());
        output.write(json.getBytes(StandardCharsets.UTF_8));
        output.flush();
        output.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        req.setCharacterEncoding("UTF-8");

        TaskStore store = TaskStore.getInstance();

        Task task = new Task();

        task.setDescription(req.getParameter("description"));
        task.setCreated(new Timestamp(System.currentTimeMillis()));
        task.setDone(false);

        store.add(task);

        resp.sendRedirect(req.getContextPath());

    }
}
