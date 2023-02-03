(async () => {
    const user = await getAuthorizedUser();
    fillHeader();
    const allRoles = await getRoles();
    let allUsers = await getAllUsers();
    fillTableBody();
    deleteModal();

    async function getAuthorizedUser() {
        const result = await fetch('http://localhost:8080/api/user/auth');
        return result.json();
    }

    async function getRoles() {
        const result = await fetch('http://localhost:8080/api/roles');
        return result.json();
    }

    function fillHeader() {
        document.getElementById("userEmail").textContent = user.email;
        document.getElementById("userRoles").textContent = user.rolesString;
    }

    async function getAllUsers() {
        let resultAllUsers = await fetch('http://localhost:8080/api/user/all');
        return resultAllUsers.json();
    }

    function fillTableBody() {
        document.getElementById("allUsersTableBody").innerHTML = '';
        allUsers.forEach(user => {
            let tableRow =
                `<tr id="user${user.id}">
                    <td hidden>${user.id}</td>
                    <td>${user.name}</td>
                    <td>${user.lastName}</td>
                    <td>${user.age}</td>
                    <td>${user.email}</td>
                    <td>${user.rolesString}</td>
                    <td>
                        <button class="btn-edit-user" data-bs-toggle="modal" data-bs-target="#modalEditUser">Edit</button>
                    </td>
                    <td>
                        <button class="btn-delete-user" data-bs-toggle="modal" data-bs-target="#modalDeleteUser">Delete</button>   
                    </td>
                </tr>`
            document.getElementById("allUsersTableBody").innerHTML += tableRow;
        });
        document.querySelectorAll('.btn-edit-user').forEach(btn => {
            onEditButton(btn);
        });
        document.querySelectorAll('.btn-delete-user').forEach(btn => {
            onDeleteButton(btn);
        });
    }

    function onEditButton(button) {
        button.addEventListener('click', (e) => {
            e.preventDefault();
            const tableRow = button.parentNode.parentNode;
            document.querySelector('#editId').value = tableRow.children[0].innerHTML;
            document.querySelector('#editName').value = tableRow.children[1].innerHTML;
            document.querySelector('#editLastName').value = tableRow.children[2].innerHTML;
            document.querySelector('#editAge').value = tableRow.children[3].innerHTML;
            document.querySelector('#editEmail').value = tableRow.children[4].innerHTML;
            document.querySelector('#editRoles').value = tableRow.children[5].innerHTML;
            document.querySelector('#editForm').ariaModal = 'show';
        });
    }

    function onDeleteButton(button) {
        button.addEventListener('click', (e) => {
            e.preventDefault();
            const tableRow = button.parentNode.parentNode;
            document.querySelector('#deleteId').value = tableRow.children[0].innerHTML;
            document.querySelector('#deleteName').value = tableRow.children[1].innerHTML;
            document.querySelector('#deleteLastName').value = tableRow.children[2].innerHTML;
            document.querySelector('#deleteAge').value = tableRow.children[3].innerHTML;
            document.querySelector('#deleteEmail').value = tableRow.children[4].innerHTML;
            document.querySelector('#deleteRoles').value = tableRow.children[5].innerHTML;
            document.querySelector('#deleteForm').ariaModal = 'show';
        })
    }

    function deleteModal() {
        document.querySelector('#deleteBtnSubmit').addEventListener('click', async (e) => {
            e.preventDefault();
            alert( `${document.querySelector('#deleteId').value}` );
            let url = `http://localhost:8080/api/user/${document.querySelector('#deleteId').value}`;
            await fetch(url, {
                method: "DELETE"
            });
            allUsers = await getAllUsers();
            fillTableBody();
            document.querySelector('#deleteForm').reset();
        });
    }

})();

