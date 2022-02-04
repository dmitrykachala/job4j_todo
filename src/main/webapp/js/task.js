function post() {
    let form = document.querySelector('.formForSend');
    let validateBtn = form.querySelector('.validateBtn');
    let description = form.querySelector("#description");
    let fields = form.querySelectorAll(".field");

    form.addEventListener('submit', function (event) {
        event.preventDefault();

        let errors = form.querySelectorAll(".error");
        let err = false;

        for (let i = 0; i < errors.length; i++) {
            errors[i].remove();
        }

        for (let i = 0; i < fields.length; i++) {
            if (!fields[i].value) {
                err = true;
                let error = document.createElement("div");
                error.className = "error";
                error.style.color = "red";
                error.innerHTML = "Поле не может быть пустым";
                form[i].parentElement.insertBefore(error, fields[i]);
            }
        }
        if (!err) {
            console.log(description.value);
            form.submit();
            init();
        }
    })
}

function init() {
    let check = document.querySelector('#hideDone');
    $.ajax({
        type: 'GET',
        url: 'task',
        dataType: 'json'
    }).done(function (data) {

        var table = document.getElementById("table");
        var rowCount = table.rows.length;
        for (var i=1; i < rowCount; i++) {
            table.deleteRow(1);
        }

        for (var task of data) {

            if (check.checked) {

                if (task.done) {

                    $('#table tr:last').after(`
                        <tr value="${task.id}" hidden>
                            <td>
                                ${task.description}
                            </td>
                            <td>${task.created}</td>
                            <td>
                                <input class="form-check-input" type="checkbox" checked/>
                            </td>
                        </tr>`);
                } else {

                    $('#table tr:last').after(`
                        <tr value="${task.id}">
                            <td>
                                ${task.description}
                            </td>
                            <td>${task.created}</td>
                            <td><input class="form-check-input" type="checkbox"/></td>
                        </tr>`);
                }
            } else {

                if (task.done) {

                    $('#table tr:last').after(`
                        <tr value="${task.id}">
                            <td>
                                ${task.description}
                            </td>
                            <td>${task.created}</td>
                            <td>
                                <input class="form-check-input" type="checkbox" checked/>
                            </td>
                        </tr>`);
                } else {

                    $('#table tr:last').after(`
                        <tr value="${task.id}">
                            <td>
                                ${task.description}
                            </td>
                            <td>${task.created}</td>
                            <td><input class="form-check-input" type="checkbox"/></td>
                        </tr>`);
                }
            }
        }

    }).fail(function (err) {
        $('#hello').text("Smth wrong");
    });
}

window.onload = function() {
    $(document).ready(init());
};