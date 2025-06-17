const db = require("../config/db");

// Get all availability
exports.getAllAvailability = (req, res) => {
  db.query("SELECT * FROM availability", (err, results) => {
    if (err) return res.status(500).json({ error: "Database error" });
    res.json(results);
  });
};

// Get availability by doctor ID
exports.getAvailabilityByDoctor = (req, res) => {
  const { doctor_id } = req.params;
  db.query(
    "SELECT * FROM availability WHERE doctor_id = ?",
    [doctor_id],
    (err, results) => {
      if (err) return res.status(500).json({ error: "Database error" });
      res.json(results);
    }
  );
};

// Add new availability
exports.createAvailability = (req, res) => {
  const { doctor_id, available_date, time_slot_id, is_booked } = req.body;
  db.query(
    "INSERT INTO availability (doctor_id, available_date, time_slot_id, is_booked) VALUES (?, ?, ?, ?)",
    [doctor_id, available_date, time_slot_id, is_booked || 0],
    (err, result) => {
      if (err) return res.status(500).json({ error: "Insertion error" });
      res.status(201).json({ message: "Availability created", id: result.insertId });
    }
  );
};

// Delete availability
exports.deleteAvailability = (req, res) => {
  const { id } = req.params;
  db.query("DELETE FROM availability WHERE id = ?", [id], (err) => {
    if (err) return res.status(500).json({ error: "Deletion error" });
    res.json({ message: "Availability deleted" });
  });
};