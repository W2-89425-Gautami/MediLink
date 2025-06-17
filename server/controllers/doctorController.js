const db = require("../config/db");

// GET /doctors
exports.getAllDoctors = (req, res, next) => {
  const query = `SELECT * FROM users WHERE role = 'DOCTOR'`;

  db.query(query, (err, results) => {
    if (err) return next(err);
    res.json(results);
  });
};

// PUT /doctors/:id/availability
exports.setAvailability = (req, res, next) => {
  const { id } = req.params;
  const { day, start_time, end_time } = req.body;

  const query = `INSERT INTO availability (doctor_id, day, start_time, end_time) VALUES (?, ?, ?, ?)`;

  db.query(query, [id, day, start_time, end_time], (err, result) => {
    if (err) return next(err);
    res.status(201).json({ message: "Availability set" });
  });
};
