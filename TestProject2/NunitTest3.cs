using NUnit.Framework;
using System;
using System.Threading;

namespace TestProject2
{
    public class UnitTest13aaaaaaaaaa1aaaaaaaaaa2aaaaaaaaaa3aaaaaaaaaa4aaaaaaaaaa5aaaaaaaaaa6aaaaaaaaaa7aaaaaaaaaa8aaaaaaaaaa9aaaaaaaaaa10aaaaaaaaaa11aaaaaaaaaa12aaaaaaaaaa113aaaaaaaaaa114aaaaaaaaaa115aaaaaaaaaa116aaaaaaaaaa117aaaaaaaaaa118aaaaaaaaaa119aaaaaaaaaa20aaaaaaaaaa121aaaaaaaaaa22
    {
        [SetUp]
        public void Setup()
        {
        }
 //[Ignore("Ignore a test")]
        [Test]
        public void Test1NunitSimplever1()
        {
            //Thread.Sleep(60000);
            Assert.Pass();
        }
        
         //[Ignore("Ignore a test")]
        [Test]
         [Category("Short")]
        [TestCase("chrome", "72.0", "Windows 10")]
        [TestCase("internet explorer", "11.0", "Windows 10")]
        public void Test2(string str1, string str2, string str3)
        {
            Thread.Sleep(30);
            Assert.Pass();
        }
        

         //[Ignore("Ignore a test")]
        [Test, Sequential]
         [Category("Long")]
        public void NunitSequentialParametersTest(
         [Values(1,2,3)] int x,
        [Values("A","B")] string s)
        {
            Thread.Sleep(40);
         Assert.Pass();
        }

     //[Ignore("Ignore a test")]    
[Test]
[Category("Long")]
public void NUnitRangeTest(
    [Values(1, 2, 3)] int x,
    [Range(0.2, 0.6, 0.2)] double d)
{
    Thread.Sleep(40);
    Assert.Pass();
}
     
        
        
        
    }
}
