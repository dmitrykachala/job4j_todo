package ru.job4j.todo.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.store.TaskStore;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.List;

public class TaskServlet extends HttpServlet {
    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json; charset=utf-8");
        OutputStream output = resp.getOutputStream();
        TaskStore store = TaskStore.getInstance();

        Response response = new Response();
        response.tasks = store.findAll();
        response.categories = store.allCategories();

        String json = GSON.toJson(response);

        output.write(json.getBytes(StandardCharsets.UTF_8));
        output.flush();
        output.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        req.setCharacterEncoding("UTF-8");

        String[] cIds = req.getParameterValues("cIds");
        TaskStore store = TaskStore.getInstance();

        Task task = new Task();

        task.setDescription(req.getParameter("description"));
        task.setUser((User) req.getSession().getAttribute("user"));
        task.setCreated(new Timestamp(System.currentTimeMillis()));
        task.setDone(false);

        store.addNewTask(task, cIds);

        resp.sendRedirect(req.getContextPath());

    }

    public class Response {
        private List<Task> tasks;
        private List<Category> categories;

        public List<Task> getTasks() {
            return tasks;
        }

        public void setTasks(List<Task> tasks) {
            this.tasks = tasks;
        }

        public List<Category> getCategories() {
            return categories;
        }

        public void setCategories(List<Category> categories) {
            this.categories = categories;
        }
    }
}
