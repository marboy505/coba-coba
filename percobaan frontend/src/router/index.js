import { createRouter, createWebHistory } from "vue-router";
import Register from "../views/register.vue";
import Login from "../views/login.vue";
import Dashboard from "../views/dashboard.vue";
import Absen from "../views/absen.vue";
import WorkOrder from "../views/workorder.vue";

const routes = [
  { path: "/", redirect: "/login" },
  { path: "/register", component: Register },
  { path: "/login", component: Login },
  { path: "/dashboard", component: Dashboard },
  { path: "/absen", component: Absen },
  { path: "/workorder", component: WorkOrder },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

// Route guarding
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem("authToken");
  if (
    (to.path === "/dashboard" ||
      to.path === "/absen" ||
      to.path === "/workorder") &&
    !token
  ) {
    next("/login");
  } else {
    next();
  }
});

export default router;
