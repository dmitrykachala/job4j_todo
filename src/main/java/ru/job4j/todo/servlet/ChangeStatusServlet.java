package ru.job4j.todo.servlet;

import ru.job4j.todo.model.Task;
import ru.job4j.todo.store.TaskStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangeStatusServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        TaskStore store = TaskStore.getInstance();
        int id = Integer.valueOf(req.getParameter("task"));
        Task task = store.findById(id);

        task.setDone(!task.isDone());

        store.replace(id, task);

    }
}
