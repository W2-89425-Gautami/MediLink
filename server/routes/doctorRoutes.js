const express = require("express");
const { body, validationResult } = require("express-validator");
const db = require("../config/db");
const verifyToken = require("../middlewares/verifyToken");
const router = express.Router();

// GET ALL
router.get("/", verifyToken, (req, res, next) => {
  db.query(
    `SELECT d.id,u.name,u.email,u.phone,d.specialization,d.bio
     FROM doctors d JOIN users u ON d.user_id=u.id`,
     (err, result) => err ? next(err) : res.json(result)
  );
});

// ADD
router.post("/add", verifyToken, [
  body("user_id").isInt(),
  body("specialization").notEmpty(),
  body("bio").optional()
], (req, res, next) => {
  if (req.user.role !== "ADMIN")
    return res.status(403).json({ error: "Access denied" });
  const errors = validationResult(req);
  if (!errors.isEmpty()) return res.status(400).json({ errors: errors.array() });
  const { user_id, specialization, bio } = req.body;
  db.query(
    "INSERT INTO doctors (user_id, specialization, bio) VALUES (?,?,?)",
    [user_id, specialization, bio],
    (err, result) => err ? next(err) : res.status(201).json({ message: "Doctor added", id: result.insertId })
  );
});

// UPDATE
router.put("/:id", verifyToken, [
  body("specialization").notEmpty(),
  body("bio").optional()
], (req, res, next) => {
  const { id } = req.params;
  const errors = validationResult(req);
  if (!errors.isEmpty()) return res.status(400).json({ errors: errors.array() });
  const { specialization, bio } = req.body;
  db.query(
    "UPDATE doctors SET specialization=?, bio=? WHERE id=?",
    [specialization, bio, id],
    err => err ? next(err) : res.json({ message: "Doctor updated" })
  );
});

// DELETE
router.delete("/:id", verifyToken, (req, res, next) => {
  db.query("DELETE FROM doctors WHERE id=?", [req.params.id], err =>
    err ? next(err) : res.json({ message: "Doctor removed" })
  );
});

module.exports = router;
