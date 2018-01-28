using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using DemoApi2.Models;

namespace DemoApi2.Controllers
{
    public class StudentController : ApiController
    {
       List<Student> students = new List<Student>
        {
            new Student {Id = 1, Name = "Derek" },
            new Student {Id = 2, Name = "Graham" }
        };
        // GET: api/Student
        public IEnumerable<Student> Get()
        {
            return students;
        }

        // GET: api/Student/5
        public Student Get(int id)
        {
            return students[id -1];
        }

        // POST: api/Student
        public void Post([FromBody]Student value)
        {
            students.Add(value);
        }

        // PUT: api/Student/5
        public void Put(int id, [FromBody]Student value)
        {
            students[id] = value;
        }

        // DELETE: api/Student/5
        public void Delete(int id)
        {
            students.Remove(students[id]);
        }
    }
}
