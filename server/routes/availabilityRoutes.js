const express = require("express");
const { body, validationResult } = require("express-validator");
const db = require("../config/db");
const verifyToken = require("../middlewares/verifyToken");
const router = express.Router();

// GET by doctor ID
router.get("/:doctorId", verifyToken, (req, res, next) => {
  db.query("SELECT * FROM availability WHERE doctor_id=?", [req.params.doctorId], (err, result) =>
    err ? next(err) : res.json(result)
  );
});

// ADD
router.post("/add", verifyToken, [
  body("doctor_id").isInt(),
  body("available_date").isISO8601(),
  body("time_slot").notEmpty()
], (req, res, next) => {
  const errors = validationResult(req);
  if (!errors.isEmpty()) return res.status(400).json({ errors: errors.array() });
  const { doctor_id, available_date, time_slot } = req.body;
  db.query(
    "INSERT INTO availability (doctor_id,available_date,time_slot) VALUES (?,?,?)",
    [doctor_id, available_date, time_slot],
    (err, result) => err ? next(err) : res.status(201).json({ message: "Slot added", id: result.insertId })
  );
});

// UPDATE
router.put("/:id", verifyToken, [
  body("available_date").isISO8601(),
  body("time_slot").notEmpty()
], (req, res, next) => {
  const errors = validationResult(req);
  if (!errors.isEmpty()) return res.status(400).json({ errors: errors.array() });
  const { available_date, time_slot } = req.body;
  db.query(
    "UPDATE availability SET available_date=?,time_slot=? WHERE id=?",
    [available_date, time_slot, req.params.id],
    err => err ? next(err) : res.json({ message: "Slot updated" })
  );
});

// DELETE
router.delete("/:id", verifyToken, (req, res, next) => {
  db.query("DELETE FROM availability WHERE id=?", [req.params.id], err =>
    err ? next(err) : res.json({ message: "Slot deleted" })
  );
});

module.exports = router;
