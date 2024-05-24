<template>
  <div class="container">
    <h2>Absen</h2>
    <button @click="checkIn" class="button">Check In</button>
    <button @click="checkOut" class="button">Check Out</button>
    <router-link to="/dashboard" class="button">Back to Dashboard</router-link>
  </div>
</template>

<script setup>
import axios from "axios";
import { ref } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();
const userId = ref(localStorage.getItem("userId"));

const checkIn = () => {
  axios
    .post(`http://localhost:8080/api/checkin/${userId.value}`)
    .then((response) => {
      console.log("Check-in successful:", response.data);
      alert("Check-in successful");
    })
    .catch((error) => {
      console.error("Check-in error:", error);
      alert("Check-in failed");
    });
};

const checkOut = () => {
  axios
    .post(`http://localhost:8080/api/checkout/${userId.value}`)
    .then((response) => {
      console.log("Check-out successful:", response.data);
      alert("Check-out successful");
    })
    .catch((error) => {
      console.error("Check-out error:", error);
      alert("Check-out failed");
    });
};

if (!userId.value) {
  router.push("/login");
}
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
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
