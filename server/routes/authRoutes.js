const express = require("express");
const { body, validationResult } = require("express-validator");
const bcrypt = require("bcryptjs");
const jwt = require("jsonwebtoken");
const db = require("../config/db");
const verifyToken = require("../middlewares/verifyToken");
const SECRET = process.env.JWT_SECRET;

const router = express.Router();

// REGISTER
router.post("/register", [
  body("name").notEmpty(),
  body("email").isEmail(),
  body("password").isLength({ min: 6 }),
  body("role").isIn(["PATIENT", "DOCTOR", "ADMIN"])
], async (req, res, next) => {
  const errors = validationResult(req);
  if (!errors.isEmpty()) return res.status(400).json({ errors: errors.array() });
  try {
    const { name, email, password, role, phone } = req.body;
    const hash = await bcrypt.hash(password, 10);
    db.query(
      "INSERT INTO users (name,email,password,role,phone) VALUES (?,?,?,?,?)",
      [name, email, hash, role, phone],
      (err, result) => {
        if (err) return next({ status: 500, message: "Registration failed", details: err });
        res.status(201).json({ message: "User registered", user_id: result.insertId });
      }
    );
  } catch (err) { next(err); }
});

// LOGIN
router.post("/login", [
  body("email").isEmail(),
  body("password").notEmpty()
], (req, res, next) => {
  const errors = validationResult(req);
  if (!errors.isEmpty()) return res.status(400).json({ errors: errors.array() });
  const { email, password } = req.body;
  db.query("SELECT * FROM users WHERE email = ?", [email], async (err, rows) => {
    if (err) return next({ status: 500, message: "Login error", details: err });
    if (!rows.length) return res.status(401).json({ error: "Invalid credentials" });
    const user = rows[0];
    const match = await bcrypt.compare(password, user.password);
    if (!match) return res.status(401).json({ error: "Invalid credentials" });
    const token = jwt.sign({ id: user.id, role: user.role }, SECRET, { expiresIn: "2h" });
    res.json({ message: "Logged in", token });
  });
});

// PROFILE
router.get("/me", verifyToken, (req, res) => {
  res.json(req.user);
});

module.exports = router;
