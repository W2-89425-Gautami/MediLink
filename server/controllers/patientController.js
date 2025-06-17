const db = require("../config/db");

// Get all patients
exports.getAllPatients = (req, res) => {
  db.query("SELECT * FROM patients", (err, results) => {
    if (err) return res.status(500).json({ error: "Database error" });
    res.json(results);
  });
};

// Get patient by ID
exports.getPatientById = (req, res) => {
  const { id } = req.params;
  db.query("SELECT * FROM patients WHERE id = ?", [id], (err, results) => {
    if (err) return res.status(500).json({ error: "Database error" });
    if (results.length === 0) return res.status(404).json({ error: "Patient not found" });
    res.json(results[0]);
  });
};

// Add new patient
exports.createPatient = (req, res) => {
  const { user_id } = req.body;
  db.query("INSERT INTO patients (user_id) VALUES (?)", [user_id], (err, result) => {
    if (err) return res.status(500).json({ error: "Insertion error" });
    res.status(201).json({ message: "Patient created", id: result.insertId });
  });
};

// Delete patient
exports.deletePatient = (req, res) => {
  const { id } = req.params;
  db.query("DELETE FROM patients WHERE id = ?", [id], (err) => {
    if (err) return res.status(500).json({ error: "Deletion error" });
    res.json({ message: "Patient deleted" });
  });
};