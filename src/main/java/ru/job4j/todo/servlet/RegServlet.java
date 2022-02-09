package ru.job4j.todo.servlet;

import ru.job4j.todo.model.User;
import ru.job4j.todo.store.TaskStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RegServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("reg.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        TaskStore store = TaskStore.getInstance();
        if (store.findUserByEmail(email) != null) {
            req.setAttribute("error", "Данный email уже используется");
            req.getRequestDispatcher("reg.jsp").forward(req, resp);
        } else {
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);
            store.add(user);
            HttpSession sc = req.getSession();
            sc.setAttribute("user", user);
            resp.sendRedirect(req.getContextPath());
        }
    }
}
