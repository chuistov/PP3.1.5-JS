(async () => {

    const user = await getAuthorizedUser();
    fillHeader();
    const allRoles = await getRoles();
    let allUsers = await getAllUsers();
    fillTableBody();
    await editModal();
    await deleteModal();
    await addForm();

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
            document.querySelector('#editRoles').innerHTML = '';
            allRoles.forEach(role => {
                if(tableRow.children[5].innerHTML.includes(role.roleName)) {
                    document.querySelector('#editRoles').innerHTML += `
                        <option value="${role.id}" selected>${role.roleName}</option>
                    `;
                } else {
                    document.querySelector('#editRoles').innerHTML += `
                        <option value="${role.id}">${role.roleName}</option>
                    `;
                }
            });
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
            /*document.querySelector('#deleteForm').ariaModal = 'show';*/
        })
    }

    async function editModal() {
        document.querySelector('#editBtnSubmit').addEventListener('click', async (e) => {
            e.preventDefault();
            const idToBeEdited = document.querySelector('#editId').value;
            await fetch(`http://localhost:8080/api/user/edit/${idToBeEdited}`, {
                method: "PATCH",
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    id: document.querySelector('#editId').value,
                    name: document.querySelector('#editName').value,
                    lastName: document.querySelector('#editLastName').value,
                    age: document.querySelector("#editAge").value,
                    email: document.querySelector('#editEmail').value,
                    password: document.querySelector('#editPassword').value,
                    rolesString: convertRolesToString(document.querySelector('#editRoles'))
                })
            });
            allUsers = await getAllUsers();
            fillTableBody();
            document.querySelector('#editForm').reset();
        });
    }

    function deleteModal() {
        document.querySelector('#deleteBtnSubmit').addEventListener('click', async (e) => {
            e.preventDefault();
            const idToBeDeleted = document.querySelector('#deleteId').value;
            const urlToDelete = `http://localhost:8080/api/user/delete/${idToBeDeleted}`;
            await fetch(urlToDelete, {
                method: "DELETE"
            });
            allUsers = await getAllUsers();
            fillTableBody();
            document.querySelector('#deleteForm').reset();
        });
    }

    function addForm() {
        document.querySelector('#addUserBtnSubmit').addEventListener('click', async (e) => {
            e.preventDefault();
            let requestResult = await fetch('http://localhost:8080/api/user', {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    id: 1,
                    name: document.querySelector('#addName').value,
                    lastName: document.querySelector('#addLastName').value,
                    age: document.querySelector("#addAge").value,
                    email: document.querySelector('#addEmail').value,
                    password: document.querySelector('#addPassword').value,
                    rolesString: convertRolesToString(document.querySelector('#addRoles'))
                })
            });
            if(requestResult.ok) {
                allUsers = await getAllUsers();
                fillTableBody();
                document.querySelector('#editForm').reset();
                alert('New user successfully added');
            } else {
                alert('Error when adding user');
            }
        });
    }

    function convertRolesToString(options) {
        let rolesString = '';
        for (let i = 0; i < options.length; i++) {
            if (options[i].selected) {
                rolesString += options[i].text + ", ";
            }
        }
        rolesString = rolesString.substring(0, rolesString.length - 2);
        return rolesString;
    }

})();