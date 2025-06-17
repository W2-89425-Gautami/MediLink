module.exports = (err, req, res, next) => {
  console.error(err); // Log detailed error
  res.status(500).json({
    message: "Something went wrong",
    error: err.message,
  });
};
