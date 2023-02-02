(async () => {
    const user = await getAuthorizedUser();
    fillHeader();
    fillTableBody();

    async function getAuthorizedUser() {
        const result = await fetch('http://localhost:8080/api/user/auth');
        return result.json();
    }

    function fillHeader() {
        document.getElementById("userEmail").textContent = user.email;
        document.getElementById("userRoles").textContent = user.rolesString;
    }

    function fillTableBody() {
        document.getElementById("authUserTableBody").innerHTML =
            `<tr style="background-color: #dcdcda">
            <td>${user.name}</td>
            <td>${user.lastName}</td>
            <td>${user.age}</td>
            <td>${user.email}</td>
            <td>${user.rolesString}</td>
        </tr>`;
    }
})();

