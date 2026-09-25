using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using MyCycleAPI.Data;
using MyCycleAPI.Models;

namespace MyCycleAPI.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class JournalEntriesController : ControllerBase
    {
        private readonly ApplicationDbContext _context;

        public JournalEntriesController(ApplicationDbContext context)
        {
            _context = context;
        }

        [HttpPost]
        public async Task<IActionResult> CreateJournal(JournalEntry journal)
        {
            if (journal.UserId <= 0)
            {
                return BadRequest("Invalid user.");
            }

            if (string.IsNullOrWhiteSpace(journal.Mood) &&
                string.IsNullOrWhiteSpace(journal.Symptoms))
            {
                return BadRequest("Please enter a symptom or mood.");
            }

            if (journal.EntryDate == default)
            {
                journal.EntryDate = DateTime.Now;
            }

            _context.JournalEntries.Add(journal);

            await _context.SaveChangesAsync();

            return Ok(new
            {
                message = "Journal entry saved.",
                journalId = journal.Id
            });
        }

        [HttpGet("{userId}")]
        public async Task<IActionResult> GetJournalEntries(int userId)
        {
            var entries = await _context.JournalEntries
                .Where(x => x.UserId == userId)
                .OrderByDescending(x => x.EntryDate)
                .ToListAsync();

            return Ok(entries);
        }
    }
} 