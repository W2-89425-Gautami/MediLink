const db = require("../config/db");

// POST /reviews
exports.addReview = (req, res, next) => {
  const { patient_id, doctor_id, rating, comment } = req.body;

  const query = `INSERT INTO reviews (patient_id, doctor_id, rating, comment) VALUES (?, ?, ?, ?)`;

  db.query(query, [patient_id, doctor_id, rating, comment], (err, result) => {
    if (err) return next(err);
    res.status(201).json({ message: "Review submitted" });
  });
};

// GET /reviews/:doctor_id
exports.getReviews = (req, res, next) => {
  const { doctor_id } = req.params;

  const query = `SELECT * FROM reviews WHERE doctor_id = ?`;

  db.query(query, [doctor_id], (err, results) => {
    if (err) return next(err);
    res.json(results);
  });
};
