(async () => {
    const user = await getAuthorizedUser();
    fillHeader();

    let allUsers = await getAllUsers();
    fillTableBody();

    async function getAuthorizedUser() {
        const result = await fetch('http://localhost:8080/api/user/auth');
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
                    <td>${user.name}</td>
                    <td>${user.lastName}</td>
                    <td>${user.age}</td>
                    <td>${user.email}</td>
                    <td>${user.rolesString}</td>
                    <td>
                        <button class="btn-edit-user"
                                data-bs-toggle="modal"
                                data-bs-target="'#modalEditUserWithId' + ${user.id}">Edit
                        </button>
                    </td>
                    <td>
                        <button class="btn-delete-user"
                                data-bs-toggle="modal"
                                data-bs-target="'#modalDeleteUserWithId' + ${user.id}">Delete
                        </button>   
                    </td>
                </tr>`
            document.querySelector('#allUsersTableBody').innerHTML += tableRow;
        });


        /*document.querySelector('#tableBody').innerHTML =
            `<tr style="background-color: #dcdcda">
            <td>${user.name}</td>
            <td>${user.lastName}</td>
            <td>${user.age}</td>
            <td>${user.email}</td>
            <td>${user.rolesString}</td>
        </tr>`;*/
    }
})();

