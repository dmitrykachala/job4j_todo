package ru.job4j.todo.servlet;

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

        store.update(store.findById(Integer.valueOf(req.getParameter("task"))));
    }
}
