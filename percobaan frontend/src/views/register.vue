<template>
  <div class="container">
    <h2>Register Page</h2>
    <!-- Form for registration -->
    <form @submit.prevent="registerUser" class="form">
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
      <select v-model="role" required class="input">
        <option value="" disabled>Select Role</option>
        <option value="admin">Admin</option>
        <option value="user">User</option>
      </select>
      <button type="submit" class="button">Register</button>
    </form>
    <!-- Link to login page -->
    <router-link to="/login" class="link"
      >Already have an account? Login here</router-link
    >
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";

// State variables
const username = ref("");
const password = ref("");
const role = ref("");

// Router instance
const router = useRouter();

// Function to handle user registration
const registerUser = async () => {
  try {
    // Make API call to register the user
    await axios.post("http://localhost:8080/api/users/register", {
      username: username.value,
      password: password.value,
      role: role.value,
    });
    // Redirect to login page after successful registration
    router.push("/login");
  } catch (error) {
    // Log and handle error if registration fails
    console.error("Error registering user:", error);
  }
};
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
  background-color: #f8f9fa;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.form {
  width: 100%;
  max-width: 400px;
}

.input {
  width: 100%;
  padding: 10px;
  margin-bottom: 15px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.button {
  width: 100%;
  padding: 10px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
}

.button:hover {
  background-color: #0056b3;
}

.link {
  color: #007bff;
  text-decoration: none;
  margin-top: 15px;
  display: inline-block;
}

.link:hover {
  text-decoration: underline;
}
</style>
