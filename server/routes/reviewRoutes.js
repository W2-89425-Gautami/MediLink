const express = require("express");
const { body, validationResult } = require("express-validator");
const db = require("../config/db");
const verifyToken = require("../middlewares/verifyToken");
const router = express.Router();

// GET reviews
router.get("/:doctorId", verifyToken, (req, res, next) => {
  db.query(
    `SELECT r.id,r.rating,r.comment,r.created_at,u.name AS patient
     FROM reviews r JOIN patients p ON r.patient_id=p.id
     JOIN users u ON p.user_id=u.id
     WHERE r.doctor_id = ?`,
    [req.params.doctorId],
    (err, result) => err ? next(err) : res.json(result)
  );
});

// ADD review
router.post("/add", verifyToken, [
  body("doctor_id").isInt(),
  body("patient_id").isInt(),
  body("rating").isInt({ min: 1, max: 5 }),
  body("comment").optional()
], (req, res, next) => {
  const errors = validationResult(req);
  if (!errors.isEmpty()) return res.status(400).json({ errors: errors.array() });
  const { doctor_id, patient_id, rating, comment } = req.body;
  db.query(
    "INSERT INTO reviews (doctor_id,patient_id,rating,comment) VALUES (?,?,?,?)",
    [doctor_id, patient_id, rating, comment],
    (err, result) => err ? next(err) : res.status(201).json({ message: "Review added", id: result.insertId })
  );
});

// UPDATE review
router.put("/:id", verifyToken, [
  body("rating").isInt({ min: 1, max: 5 }),
  body("comment").optional()
], (req, res, next) => {
  const errors = validationResult(req);
  if (!errors.isEmpty()) return res.status(400).json({ errors: errors.array() });
  const { rating, comment } = req.body;
  db.query(
    "UPDATE reviews SET rating=?,comment=? WHERE id=? AND patient_id=?",
    [rating, comment, req.params.id, req.user.id],
    (err, result) => err ? next(err) : res.json({ message: "Review updated" })
  );
});

// DELETE review
router.delete("/:id", verifyToken, (req, res, next) => {
  db.query("DELETE FROM reviews WHERE id=? AND patient_id=?", [req.params.id, req.user.id], err =>
    err ? next(err) : res.json({ message: "Review deleted" })
  );
});

module.exports = router;
