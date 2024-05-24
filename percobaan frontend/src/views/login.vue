<template>
  <div class="container">
    <h2>Login Page</h2>
    <form @submit.prevent="loginUser" class="form">
      <input
        type="text"
        v-model="username"
        placeholder="Username"
        required
        class="input"
      />
      <input
        type="password"
        v-model="password"
        placeholder="Password"
        required
        class="input"
      />
      <button type="submit" class="button">Login</button>
    </form>
    <router-link to="/register" class="link">
      Don't have an account? Register here
    </router-link>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";

// State variables
const username = ref("");
const password = ref("");
const router = useRouter();

// Function to handle user login
const loginUser = async () => {
  try {
    // Make API call to login
    const response = await axios.post("http://localhost:8080/api/users/login", {
      username: username.value,
      password: password.value,
    });

    // Extract token and userId from response
    const token = response.data.token;
    const userId = response.data.userId;

    // Check if token or userId is missing in response
    if (!token || !userId) {
      console.error("Missing token or userId in response");
      return;
    }

    // Store token and userId in local storage
    localStorage.setItem("authToken", token);
    localStorage.setItem("userId", userId);

    // Redirect to dashboard after successful login
    console.log("Login successful, redirecting to dashboard");
    router.push("/dashboard");
  } catch (error) {
    // Handle login errors
    if (error.response && error.response.status === 401) {
      console.error("Invalid username or password");
    } else {
      console.error("Error logging in:", error.message);
    }
  }
};
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.form {
  width: 300px;
  margin-bottom: 20px;
}

.input {
  width: 100%;
  padding: 10px;
  margin-bottom: 10px;
}

.button {
  width: 100%;
  padding: 10px;
  background-color: blue;
  color: white;
  border: none;
  cursor: pointer;
}

.button:hover {
  background-color: darkblue;
}

.link {
  color: blue;
  text-decoration: none;
  margin-top: 10px;
}

.link:hover {
  text-decoration: underline;
}
</style>
