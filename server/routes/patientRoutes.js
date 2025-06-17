const express = require("express");
const { body, validationResult } = require("express-validator");
const db = require("../config/db");
const verifyToken = require("../middlewares/verifyToken");
const router = express.Router();

// GET ALL (ADMIN)
router.get("/", verifyToken, (req, res, next) => {
  if (req.user.role !== "ADMIN") return res.status(403).json({ error: "Access denied" });
  db.query(
    `SELECT p.id,u.name,u.email,u.phone
     FROM patients p JOIN users u ON p.user_id=u.id`,
    (err, result) => err ? next(err) : res.json(result)
  );
});

// REGISTER PATIENT
router.post("/register", verifyToken, [
  body("user_id").isInt()
], (req, res, next) => {
  if (req.user.role !== "PATIENT") return res.status(403).json({ error: "Access denied" });
  const errors = validationResult(req);
  if (!errors.isEmpty()) return res.status(400).json({ errors: errors.array() });
  db.query("INSERT INTO patients (user_id) VALUES (?)", [req.body.user_id], (err, result) =>
    err ? next(err) : res.status(201).json({ message: "Patient registered", id: result.insertId })
  );
});

// UPDATE (Patient only)
router.put("/:id", verifyToken, (req, res, next) => {
  if (req.user.role !== "PATIENT") return res.status(403).json({ error: "Access denied" });
  db.query(
    `UPDATE users SET phone=? WHERE id=(SELECT user_id FROM patients WHERE id=?)`,
    [req.body.phone, req.params.id],
    err => err ? next(err) : res.json({ message: "Profile updated" })
  );
});

// DELETE (ADMIN)
router.delete("/:id", verifyToken, (req, res, next) => {
  if (req.user.role !== "ADMIN") return res.status(403).json({ error: "Access denied" });
  db.query("DELETE FROM patients WHERE id=?", [req.params.id], err =>
    err ? next(err) : res.json({ message: "Patient removed" })
  );
});

module.exports = router;
