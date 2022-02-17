<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<html>
<head>

        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
              integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

        <!-- Optional JavaScript -->
        <!-- jQuery first, then Popper.js, then Bootstrap JS -->
        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
                integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
                integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
                integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
                integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
                integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
        <script src="https://code.jquery.com/jquery-3.4.1.min.js" ></script>

        <script src="js/task.js" ></script>

</head>
<body>
    <div class="container-fluid">
        <%@include file="header.jsp" %>
        <c:if test="${user != null}">
        <form class="formForSend" role="form" action="<%=request.getContextPath()%>/task" method="post">
            <div class="form-group">
                <label for="description">Добавить задание</label>
                <input type="text" class="form-control field" id="description"  name="description" placeholder="Описание">
            </div>

            <div>
                <label class="col-form-label col-sm-3" for="cIds" style="font-weight: 900">Список категорий</label>
                <div class="col-sm-5">
                    <select class="form-control field" name="cIds" id="cIds" multiple>
                        <option>тест</option>
                    </select>
                </div>
            </div>

            <br>
            <button type="submit" class="validateBtn" id="postBtn" onclick="post()">Добавить</button>
            <br>
        </form>
            <div class="form-check">
                <input class="form-check-input" type="checkbox" value="" id="hideDone" onchange="init()" checked>
                <label class="form-check-label" for="hideDone">
                    Скрыть выполненные
                </label>
            </div>

            <table class="table table-bordered" id="table">
                <thead>
                    <tr class="active">
                        <th>Описание</th>
                        <th>Категория</th>
                        <th>Дата создания</th>
                        <th>Автор задания</th>
                        <th>Статус выполнения</th>
                    </tr>
                </thead>
                <tbody>

                </tbody>
            </table>
        </c:if>
    </div>

</body>
</html>
