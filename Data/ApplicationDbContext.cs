using Microsoft.EntityFrameworkCore;
using MyCycleAPI.Models;
using System.Collections.Generic;

namespace MyCycleAPI.Data
{
    public class ApplicationDbContext : DbContext
    {
        public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options)
            : base(options)
        {
        }

        public DbSet<User> Users { get; set; }

        public DbSet<PeriodEntry> PeriodEntries { get; set; }

        public DbSet<JournalEntry> JournalEntries { get; set; }
    }
}