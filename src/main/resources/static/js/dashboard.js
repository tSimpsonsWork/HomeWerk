const welcomeMessage = document.getElementById("welcome-message");
const logoutButton = document.getElementById("logout-button");

async function loadCurrentUser() {

    try {

        const response = await fetch("/auth/me");

        if (response.status === 401) {
            window.location.href = "/login.html";
            return;
        }

        if (!response.ok) {
            welcomeMessage.textContent = "Unable to load user.";
            return;
        }

        const user = await response.json();

        welcomeMessage.textContent = `Welcome, ${user.displayName}`;

    } catch (error) {

        welcomeMessage.textContent = "Unable to connect to HoMeK.";

    }
}

async function logout() {

    try {

        const response = await fetch("/auth/logout", {
            method: "POST"
        });

        if (response.ok) {
            window.location.href = "/login.html";
        }

    } catch (error) {

        console.error("Logout failed.");

    }
}

logoutButton.addEventListener("click", logout);

loadCurrentUser();