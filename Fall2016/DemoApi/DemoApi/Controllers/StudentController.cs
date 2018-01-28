using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using DemoApi.Models;

namespace DemoApi.Controllers
{
    public class StudentController : ApiController
    {
        List<Student> students = new List<Student>()
        {
            new Student {Name = "Tanner",Id = 1 },
            new Student {Name = "Kate", Id = 2 }
        };
       
        // GET: api/Student
        public IEnumerable<Student> Get()
        {
            return students;
        }

        // GET: api/Student/5
        public Student Get(int id)
        {
            return students[id];
        }

        // POST: api/Student
        public void Post([FromBody]Student value)
        {

        }

        // PUT: api/Student/5
        public void Put(int id, [FromBody]string value)
        {
        }

        // DELETE: api/Student/5
        public void Delete(int id)
        {
        }
    }
}
