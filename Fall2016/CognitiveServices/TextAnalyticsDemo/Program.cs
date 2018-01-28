using System;
using System.Globalization;
using System.Net.Http.Headers;
using System.Text;
using System.Net.Http;
using System.Threading.Tasks;
using System.Web;
using Newtonsoft.Json;
using System.Collections.Generic;
using System.Threading;

namespace TextAnalyticsDemo
{
    static class Program
    {
        /// <summary>
        /// Azure portal URL.
        /// </summary>
        private const string BaseUrl = "https://westus.api.cognitive.microsoft.com/";

        /// <summary>
        /// Your account key goes here.
        /// </summary>
        private const string AccountKey = "48bf818296204dcda91cef83a7f6a3aa";

        /// <summary>
        /// Maximum number of languages to return in language detection API.
        /// </summary>
        private const int NumLanguages = 1;

        static void Main()
        {
            ExecuteDemo();
        }

        private static void ExecuteDemo()
        {
            bool t = true;
            while (t)
            {
                Console.WriteLine("Give me a string:");
                String input = Console.ReadLine();
                MakeRequests(input);
                Thread.Sleep(3000);
                Console.WriteLine("Do you want to go again? (y or n)");
                string answer = Console.ReadLine();
                if (answer.ToLower().Equals("n"))
                    t = false;
                else if (!answer.ToLower().Equals("y"))
                {
                    Console.WriteLine("Invalid input. Hit Enter to exit");
                    Console.ReadLine();
                }
            }
        }

        static async Task MakeRequests(string input)
        {
            using (var client = new HttpClient())
            {
                client.BaseAddress = new Uri(BaseUrl);

                // Request headers.
                client.DefaultRequestHeaders.Add("Ocp-Apim-Subscription-Key", AccountKey);
                client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));

                // Request body. Insert your text data here in JSON format.
                byte[] byteData = Encoding.UTF8.GetBytes("{\"documents\":[" +
                    "{\"id\":\"1\",\"text\":\"" + input + "\"}]}");

                // Detect key phrases:
                var uri = "text/analytics/v2.0/keyPhrases";
                var response = await CallEndpoint(client, uri, byteData);

                var keyPhrase = GetResponse<KeyPhraseResponse>(response);
                Console.WriteLine("\nDetect key phrases:\n" + keyPhrase.ToString());

                // Detect language:
                var queryString = HttpUtility.ParseQueryString(string.Empty);
                queryString["numberOfLanguagesToDetect"] = NumLanguages.ToString(CultureInfo.InvariantCulture);
                uri = "text/analytics/v2.0/languages?" + queryString;
                response = await CallEndpoint(client, uri, byteData);
                var lang = GetResponse<LanguageResponse>(response);

                Console.WriteLine("\nDetect language response:\n" + lang.ToString());

                

                // Detect sentiment:
                uri = "text/analytics/v2.0/sentiment";
                response = await CallEndpoint(client, uri, byteData);
                var sent = GetResponse<SentimentResponse>(response);
                Console.WriteLine("\nDetect sentiment response:\n" + sent.ToString());
            }
        }

        private static T GetResponse<T>(string response)
        {
            return JsonConvert.DeserializeObject<T>(response);
        }

        static async Task<String> CallEndpoint(HttpClient client, string uri, byte[] byteData)
        {
            using (var content = new ByteArrayContent(byteData))
            {
                content.Headers.ContentType = new MediaTypeHeaderValue("application/json");
                var response = await client.PostAsync(uri, content);
                return await response.Content.ReadAsStringAsync();
            }
        }
    }

    class KeyPhraseResponse
    {
        [JsonProperty]
        public List<KeyPhraseDocument> documents { get; set; }
        [JsonProperty]
        public List<string> errors { get; set; }

        public override string ToString()
        {
            StringBuilder result = new StringBuilder();
            foreach(KeyPhraseDocument doc in documents)
            {
                result.Append(doc.ToString());
            }
            return result.ToString();
        }
    }

    class KeyPhraseDocument
    {
        [JsonProperty]
        public List<string> keyPhrases { get; set; }
        [JsonProperty]
        public string id { get; set; }

        public override string ToString()
        {
            StringBuilder result = new StringBuilder();
            result.Append("Id: " + id + "\n");
            result.Append("Key Phrases: ");
            foreach(string phrase in keyPhrases)
            {
                result.Append(phrase + ",");
            }
            result.Remove(result.Length - 1,1);
            return result.ToString();
        }
    }

    class SentimentResponse
    {
        [JsonProperty]
        public List<SentimentDocument> documents { get; set; }
        [JsonProperty]
        public List<string> errors { get; set; }

        public override string ToString()
        {
            StringBuilder result = new StringBuilder();
            foreach (SentimentDocument doc in documents)
            {
                result.Append(doc.ToString());
            }
            return result.ToString();
        }
    }

    class SentimentDocument
    {
        [JsonProperty]
        public double score { get; set; }
        [JsonProperty]
        public string id { get; set; }

        public override string ToString()
        {
            return "Id: " + id + " Score: " + score;
        }

    }

    class LanguageResponse
    {

        [JsonProperty]
        public List<LanguageDocument> documents { get; set; }
        [JsonProperty]
        public List<string> errors { get; set; }

        public override string ToString()
        {
            StringBuilder result = new StringBuilder();
            foreach (LanguageDocument doc in documents)
            {
                result.Append(doc.ToString());
            }
            return result.ToString();
        }
    }

    class LanguageDocument
    {
        [JsonProperty]
        public List<Language> detectedLanguages { get; set; }

        public override string ToString()
        {
            StringBuilder result = new StringBuilder();
            foreach (Language lang in detectedLanguages)
            {
                result.Append(lang.ToString());
            }
            return result.ToString();
        }
    }

    class Language
    {
        public string name { get; set; }
        public string iso6391Name { get; set; }
        public double score { get; set; }

        public override string ToString()
        {
            return "Name: " + name + " Codename: " + iso6391Name + " Confidence: " + score;
        }
    }
}
