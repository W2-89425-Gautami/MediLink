const express = require("express");
const { body, validationResult } = require("express-validator");
const db = require("../config/db");
const verifyToken = require("../middlewares/verifyToken");
const router = express.Router();

// GET by query
router.get("/", verifyToken, (req, res, next) => {
  const { doctor_id, patient_id } = req.query;
  let sql = "SELECT * FROM appointments";
  const params = [];
  if (doctor_id) { sql += " WHERE doctor_id=?"; params.push(doctor_id); }
  if (patient_id) { sql += " WHERE patient_id=?"; params.push(patient_id); }
  db.query(sql, params, (err, result) => err ? next(err) : res.json(result));
});

// BOOK
router.post("/add", verifyToken, [
  body("doctor_id").isInt(),
  body("patient_id").isInt(),
  body("appointment_date").isISO8601(),
  body("time_slot").notEmpty()
], (req, res, next) => {
  const errors = validationResult(req);
  if (!errors.isEmpty()) return res.status(400).json({ errors: errors.array() });
  const { doctor_id, patient_id, appointment_date, time_slot } = req.body;
  db.query(
    `INSERT INTO appointments (doctor_id,patient_id,appointment_date,time_slot)
     VALUES (?,?,?,?)`,
    [doctor_id, patient_id, appointment_date, time_slot],
    (err, result) => err ? next(err) : res.status(201).json({ message: "Booked", id: result.insertId })
  );
});

// UPDATE STATUS
router.patch("/:id/status", verifyToken, [
  body("status").isIn(["PENDING","CONFIRMED","CANCELLED","COMPLETED"])
], (req, res, next) => {
  const errors = validationResult(req);
  if (!errors.isEmpty()) return res.status(400).json({ errors: errors.array() });
  db.query(
    "UPDATE appointments SET status=? WHERE id=?", 
    [req.body.status, req.params.id], 
    err => err ? next(err) : res.json({ message: "Status updated" })
  );
});

// DELETE
router.delete("/:id", verifyToken, (req, res, next) => {
  db.query("DELETE FROM appointments WHERE id=?", [req.params.id], err =>
    err ? next(err) : res.json({ message: "Appointment deleted" })
  );
});

module.exports = router;
