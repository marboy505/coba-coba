<template>
  <div class="container">
    <h2>Dashboard</h2>
    <p>Welcome, {{ username }}</p>
    <router-link to="/absen" class="button">Absen</router-link>
    <router-link to="/workorder" class="button">Work Order</router-link>
    <button @click="logoutUser" class="button">Logout</button>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";

const router = useRouter();
const username = ref("");

onMounted(() => {
  const userId = localStorage.getItem("userId");
  if (!userId) {
    console.error("User ID not found in local storage");
    router.push("/login");
    return;
  }

  const token = localStorage.getItem("authToken");
  if (!token) {
    console.error("Auth token not found in local storage");
    router.push("/login");
    return;
  }

  axios
    .get(`http://localhost:8080/api/users/${userId}`, {
      headers: { Authorization: `Bearer ${token}` },
    })
    .then((response) => {
      username.value = response.data.username;
    })
    .catch((error) => {
      console.error("Error fetching user data:", error);
      router.push("/login");
    });
});

const logoutUser = () => {
  localStorage.removeItem("authToken");
  localStorage.removeItem("userId");
  router.push("/login");
};
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
}

p {
  margin-bottom: 20px;
}

.button {
  width: 100%;
  padding: 10px;
  background-color: blue;
  color: white;
  border: none;
  cursor: pointer;
  margin-bottom: 10px;
}
</style>
