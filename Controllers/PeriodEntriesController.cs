using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using MyCycleAPI.Data;
using MyCycleAPI.Models;

namespace MyCycleAPI.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class PeriodEntriesController : ControllerBase
    {
        private readonly ApplicationDbContext _context;

        public PeriodEntriesController(ApplicationDbContext context)
        {
            _context = context;
        }

        [HttpPost]
        public async Task<IActionResult> CreatePeriod(PeriodEntry period)
        {
            if (period.UserId <= 0)
            {
                return BadRequest("Invalid user.");
            }

            if (period.StartDate > period.EndDate)
            {
                return BadRequest("Start date cannot be after end date.");
            }

            _context.PeriodEntries.Add(period);

            await _context.SaveChangesAsync();

            return Ok(new
            {
                message = "Period entry saved.",
                periodId = period.Id
            });
        }

        [HttpGet("{userId}")]
        public async Task<IActionResult> GetPeriods(int userId)
        {
            var periods = await _context.PeriodEntries
                .Where(x => x.UserId == userId)
                .OrderByDescending(x => x.StartDate)
                .ToListAsync();

            return Ok(periods);
        }
    }
}