$(document).ready(function() {
    function loadAuthors() {
        $.ajax({
            url: '/api/authors',
            method: 'GET',
            dataType: 'json',
            success: function(data) {
                const $select = $('#authorNames');
                $select.empty();

                const tbody = $('#authorList tbody');
                tbody.empty();
                data.forEach(author => {
                    $select.append(`<option value="${author.name}">${author.name}</option>`);
                    tbody.append(`
                        <tr>
                            <td>${author.id}</td>
                            <td>${author.name}</td>
                            <td>${author.dateOfBirth ? author.dateOfBirth : ''}</td>
                        </tr>
                    `);
                });
            },
            error: function(error) {
                console.error('Error loading authors:', error);
            }
        });
    }
    loadAuthors();

    $('#bookForm').on('submit', function(event) {
        event.preventDefault(); // Ngăn chặn hành vi gửi biểu mẫu mặc định

        const code = $('#code').val();
        const title = $('#title').val();
        const quantity = $('#quantity').val();
        const authorNames = $('#authorNames').val();

        const bookData = {
            code: code,
            title: title,
            quantity: parseInt(quantity),
            authorNames: authorNames
        };

        fetch('/api/books', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(bookData)
        })
            .then(response => response.json())
            .then(data => {
                console.log('Success:', data);
                updateBookList();
            })
            .catch(error => {
                console.error('Error:', error);
            });
    });

    function updateBookList() {
        fetch('/api/books')
            .then(response => response.json())
            .then(books => {
                const tbody = $('#bookList tbody');
                tbody.empty(); // Xóa các hàng hiện tại

                books.forEach(book => {
                    tbody.append(`
                        <tr>
                            <td>${book.code}</td>
                            <td>${book.title}</td>
                            <td>${book.quantity}</td>
                        </tr>
                    `);
                });
            })
            .catch(error => {
                console.error('Error fetching books:', error);
            });
    }
    updateBookList();

    $('#authorForm').on('submit', function(event) {
        event.preventDefault(); // Ngăn chặn hành vi gửi biểu mẫu mặc định

        const name = $('#name').val();
        const dateOfBirth = $('#dob').val();
        const biology = $('#biology').val();

        const authorData = {
            name: name,
            dateOfBirth: dateOfBirth,
            biology: biology,
        };

        fetch('/api/authors', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(authorData)
        })
            .then(response => response.json())
            .then(data => {
                console.log('Success:', data);
                loadAuthors();
            })
            .catch(error => {
                console.error('Error:', error);
            });
    });

});
