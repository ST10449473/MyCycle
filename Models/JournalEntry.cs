namespace MyCycleAPI.Models
{
    public class JournalEntry
    {
        public int Id { get; set; }

        public int UserId { get; set; }

        public string Symptoms { get; set; } = string.Empty;

        public string Mood { get; set; } = string.Empty;

        public DateTime EntryDate { get; set; }
    }
}