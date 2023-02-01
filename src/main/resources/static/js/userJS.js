(async () => {
    const result = await fetch('http://localhost:8080/api/user/auth');
    const user = await result.json();
    document.getElementById("userEmail").textContent = user.email;
    document.getElementById("userRoles").textContent = user.rolesString;
    document.querySelector('#tableBody').innerHTML =
        `<tr style="background-color: #dcdcda">
            <td>${user.name}</td>
            <td>${user.lastName}</td>
            <td>${user.age}</td>
            <td>${user.email}</td>
            <td>${user.rolesString}</td>
        </tr>`;
})();
