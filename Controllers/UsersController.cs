using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using MyCycleAPI.Data;
using MyCycleAPI.Models;
using System.Security.Cryptography;
using System.Text;

namespace MyCycleAPI.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class UsersController : ControllerBase
    {
        private readonly ApplicationDbContext _context;

        public UsersController(ApplicationDbContext context)
        {
            _context = context;
        }

        [HttpPost("register")]
        public async Task<IActionResult> Register(User user)
        {
            if (string.IsNullOrWhiteSpace(user.Name) ||
                string.IsNullOrWhiteSpace(user.Email) ||
                string.IsNullOrWhiteSpace(user.PasswordHash))
            {
                return BadRequest("Name, email and password are required.");
            }

            var existingUser = await _context.Users
                .FirstOrDefaultAsync(x => x.Email == user.Email);

            if (existingUser != null)
            {
                return BadRequest("An account with this email already exists.");
            }

            user.PasswordHash = HashPassword(user.PasswordHash);

            _context.Users.Add(user);
            await _context.SaveChangesAsync();

            return Ok(new
            {
                message = "Registration successful.",
                userId = user.Id,
                name = user.Name,
                email = user.Email
            });
        }

        [HttpPost("login")]
        public async Task<IActionResult> Login(User user)
        {
            if (string.IsNullOrWhiteSpace(user.Email) ||
                string.IsNullOrWhiteSpace(user.PasswordHash))
            {
                return BadRequest("Email and password are required.");
            }

            var passwordHash = HashPassword(user.PasswordHash);

            var existingUser = await _context.Users
                .FirstOrDefaultAsync(x =>
                    x.Email == user.Email &&
                    x.PasswordHash == passwordHash);

            if (existingUser == null)
            {
                return Unauthorized("Invalid email or password.");
            }

            return Ok(new
            {
                message = "Login successful.",
                userId = existingUser.Id,
                name = existingUser.Name,
                email = existingUser.Email
            });
        }

        private static string HashPassword(string password)
        {
            using var sha256 = SHA256.Create();

            var bytes = Encoding.UTF8.GetBytes(password);
            var hash = sha256.ComputeHash(bytes);

            return Convert.ToBase64String(hash);
        }
    }
}