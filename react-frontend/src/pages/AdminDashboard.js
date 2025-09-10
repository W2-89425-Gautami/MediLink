import React, { useEffect, useState } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import "./AdminDashboard.css"; // for styles

const AdminDashboard = () => {
  const [stats, setStats] = useState({
    doctors: 0,
    patients: 0,
    appointments: 0,
    specializations: 0,
  });

  useEffect(() => {
    const fetchStats = async () => {
      try {
        const token = localStorage.getItem("token");
        const headers = { Authorization: `Bearer ${token}` };

        // Fetching counts from backend (you can create /api/admin/stats in backend if not existing)
        const res = await axios.get("http://localhost:8080/api/admin/stats", { headers });
        setStats(res.data);
      } catch (error) {
        console.error("Error fetching stats:", error);
      }
    };

    fetchStats();
  }, []);

  return (
    <div className="admin-dashboard">
      <h1>Admin Dashboard</h1>
      <div className="stats-grid">
        <div className="card">
          <h2>{stats.doctors}</h2>
          <p>Total Doctors</p>
        </div>
        <div className="card">
          <h2>{stats.patients}</h2>
          <p>Total Patients</p>
        </div>
        <div className="card">
          <h2>{stats.appointments}</h2>
          <p>Total Appointments</p>
        </div>
        <div className="card">
          <h2>{stats.specializations}</h2>
          <p>Specializations</p>
        </div>
      </div>

      <div className="links">
        <Link to="/admin/manage-doctors">Manage Doctors</Link>
        <Link to="/admin/manage-appointments">Manage Appointments</Link>
        <Link to="/admin/manage-specializations">Manage Specializations</Link>
      </div>
    </div>
  );
};

export default AdminDashboard;
