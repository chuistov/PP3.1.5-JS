(async () => {
    let resultUser = await fetch('http://localhost:8080/api/user/auth');
    let user = await resultUser.json();
    document.getElementById("userEmail").textContent = user.email;
    document.getElementById("userRoles").textContent = user.rolesString;

    let resultAllUsers = await fetch('http://localhost:8080/api/user/all');
    let allUsers = await resultAllUsers.json();


})();