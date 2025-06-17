const db = require("../config/db");
const jwt = require("jsonwebtoken");
const bcrypt = require("bcrypt");

// POST /register
exports.register = async (req, res, next) => {
  const { name, email, password, role, phone } = req.body;

  try {
    const hashedPassword = await bcrypt.hash(password, 10);
    const query = `INSERT INTO users (name, email, password, role, phone) VALUES (?, ?, ?, ?, ?)`;

    db.query(query, [name, email, hashedPassword, role, phone], (err, result) => {
      if (err) return next(err);
      res.status(201).json({ message: "User registered successfully" });
    });
  } catch (error) {
    next(error);
  }
};

// POST /login
exports.login = (req, res, next) => {
  const { email, password } = req.body;
  const query = `SELECT * FROM users WHERE email = ?`;

  db.query(query, [email], async (err, results) => {
    if (err) return next(err);

    if (results.length === 0) return res.status(404).json({ message: "User not found" });

    const user = results[0];
    const isMatch = await bcrypt.compare(password, user.password);

    if (!isMatch) return res.status(401).json({ message: "Invalid credentials" });

    const token = jwt.sign({ id: user.id, role: user.role }, "process.env.JWT_SECRET", { expiresIn: "1h" });

    res.status(200).json({
      message: "Login successful",
      token,
      user: { id: user.id, name: user.name, email: user.email, role: user.role },
    });
  });
};
