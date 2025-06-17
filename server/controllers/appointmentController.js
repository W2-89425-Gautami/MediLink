const db = require("../config/db");

// POST /appointments
exports.bookAppointment = (req, res, next) => {
  const { patient_id, doctor_id, date, time } = req.body;

  const query = `INSERT INTO appointments (patient_id, doctor_id, date, time, status) VALUES (?, ?, ?, ?, 'PENDING')`;

  db.query(query, [patient_id, doctor_id, date, time], (err, result) => {
    if (err) return next(err);
    res.status(201).json({ message: "Appointment booked" });
  });
};

// GET /appointments/user/:id
exports.getAppointmentsByUser = (req, res, next) => {
  const id = req.params.id;

  const query = `SELECT * FROM appointments WHERE patient_id = ? OR doctor_id = ?`;

  db.query(query, [id, id], (err, results) => {
    if (err) return next(err);
    res.json(results);
  });
};
