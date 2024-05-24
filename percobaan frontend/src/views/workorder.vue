<template>
  <div class="container">
    <h2>Work Order</h2>
    <!-- Only show create form if user is admin -->
    <button v-if="isAdmin" @click="showCreateForm = true" class="button">
      Create Work Order
    </button>
    <div v-if="showCreateForm" class="form">
      <input
        type="text"
        v-model="newWorkOrder.title"
        placeholder="Title"
        required
        class="input"
      />
      <textarea
        v-model="newWorkOrder.description"
        placeholder="Description"
        required
        class="input"
      ></textarea>

      <select v-model="newWorkOrder.status" required class="input">
        <option value="" disabled>Select Role</option>
        <option value="pending">Pending</option>
        <option value="open">Open</option>
        <option value="done">Done</option>
      </select>

      <!-- Only show if user is admin -->
      <select v-if="isAdmin" v-model="newWorkOrder.assignedTo" class="input">
        <option disabled value="">Select Assigned To</option>
        <option v-for="user in users" :key="user.id" :value="user.id">
          {{ user.username }}
        </option>
      </select>
      <button @click="createWorkOrder" class="button">Submit</button>
    </div>
    <div v-if="showSuccessAlert" class="alert">
      Work Order created successfully!
    </div>
    <div v-if="showErrorAlert" class="alert error">
      {{ errorMessage }}
    </div>
    <table v-if="workOrders.length" class="table">
      <thead>
        <tr>
          <th>Title</th>
          <th>Description</th>
          <th>Status</th>
          <th>Assigned To</th>
          <th v-if="isAdmin">Action</th>
          <!-- Added action column -->
        </tr>
      </thead>
      <tbody>
        <tr v-for="workOrder in workOrders" :key="workOrder.id">
          <td>{{ workOrder.title }}</td>
          <td>{{ workOrder.description }}</td>
          <td>{{ workOrder.status }}</td>
          <td>{{ workOrder.assignedTo.username }}</td>
          <td v-if="isAdmin">
            <!-- Edit button -->
            <button
              v-if="isAdmin"
              @click="editWorkOrder(workOrder)"
              class="button"
            >
              Edit
            </button>
            <!-- Delete button -->
            <button
              v-if="isAdmin"
              @click="deleteWorkOrder(workOrder.id)"
              class="button"
            >
              Delete
            </button>
          </td>
        </tr>
      </tbody>
    </table>
    <router-link to="/dashboard" class="button">Back to Dashboard</router-link>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";
import { useRouter } from "vue-router";

// State variables
const userId = ref(localStorage.getItem("userId"));
const workOrders = ref([]);
const showCreateForm = ref(false);
const showSuccessAlert = ref(false);
const showErrorAlert = ref(false);
const errorMessage = ref("");
const users = ref([]);
const newWorkOrder = ref({
  title: "",
  description: "",
  status: "",
  assignedTo: "",
});

// Simulate user roles for demo purposes
const isAdmin = ref(false);

// Router instance
const router = useRouter();

// Fetch work orders for the user
const fetchWorkOrders = () => {
  axios
    .get("http://localhost:8080/api/workorders")
    .then((response) => {
      workOrders.value = response.data;
    })
    .catch((error) => {
      console.error("Error fetching work orders:", error);
    });
};

// Fetch all users
const fetchUsers = () => {
  axios
    .get("http://localhost:8080/api/users/all")
    .then((response) => {
      users.value = response.data;
    })
    .catch((error) => {
      console.error("Error fetching users:", error);
    });
};

// Create a new work order
const createWorkOrder = () => {
  // Reset alerts
  showSuccessAlert.value = false;
  showErrorAlert.value = false;
  errorMessage.value = "";

  // Validate data
  if (
    !newWorkOrder.value.title ||
    !newWorkOrder.value.description ||
    !newWorkOrder.value.status ||
    !newWorkOrder.value.assignedTo
  ) {
    showErrorAlert.value = true;
    errorMessage.value = "All fields are required.";
    return;
  }

  // Find the selected user object based on the assignedTo ID
  const assignedToUser = users.value.find(
    (user) => user.id === newWorkOrder.value.assignedTo
  );

  // Create the work order object with the complete assignedTo user object
  const workOrderData = {
    title: newWorkOrder.value.title,
    description: newWorkOrder.value.description,
    status: newWorkOrder.value.status,
    assignedTo: assignedToUser, // Use the complete user object
  };

  axios
    .post("http://localhost:8080/api/workorders/created", workOrderData) // Send the complete work order object
    .then(() => {
      showSuccessAlert.value = true;
      showCreateForm.value = false;
      fetchWorkOrders();
    })
    .catch((error) => {
      console.error("Error creating work order:", error);
      showErrorAlert.value = true;
      if (
        error.response &&
        error.response.data &&
        error.response.data.message
      ) {
        errorMessage.value = error.response.data.message;
      } else {
        errorMessage.value = "An error occurred while creating the work order.";
      }
    });
};

// Fetch current user to check if admin
const fetchCurrentUser = () => {
  axios
    .get(`http://localhost:8080/api/users/${userId.value}`)
    .then((response) => {
      isAdmin.value = response.data.role === "admin";
    })
    .catch((error) => {
      console.error("Error fetching current user:", error);
    });
};

// Get username by user ID
const getUsernameById = (id) => {
  const user = users.value.find((user) => user.id === id);
  return user ? user.username : "Unknown";
};

// Edit a work order
const editWorkOrder = (workOrder) => {
  // Set the newWorkOrder object
  // Set the newWorkOrder object with the values of the work order to edit
  newWorkOrder.value.title = workOrder.title;
  newWorkOrder.value.description = workOrder.description;
  newWorkOrder.value.status = workOrder.status;
  newWorkOrder.value.assignedTo = workOrder.assignedTo.id; // Set assignedTo ID

  // Show the create form
  showCreateForm.value = true;
};

// Delete a work order
const deleteWorkOrder = (id) => {
  axios
    .delete(`http://localhost:8080/api/workorders/${id}`)
    .then(() => {
      // Remove the deleted work order from the list
      workOrders.value = workOrders.value.filter(
        (workOrder) => workOrder.id !== id
      );
    })
    .catch((error) => {
      console.error("Error deleting work order:", error);
    });
};

// Fetch data on component mount
onMounted(() => {
  if (!userId.value) {
    router.push("/login");
  } else {
    fetchWorkOrders();
    fetchUsers();
    fetchCurrentUser();
  }
});
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
  margin-bottom: 10px;
}

.alert {
  color: green;
  margin-bottom: 20px;
}

.alert.error {
  color: red;
}

.table {
  width: 80%;
  margin-top: 20px;
  border-collapse: collapse;
}

.table th,
.table td {
  border: 1px solid #ddd;
  padding: 8px;
}

.table th {
  background-color: #f2f2f2;
  text-align: left;
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
