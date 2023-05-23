using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Diagnostics.Contracts;
using Xunit;


namespace TestProject11
{
    public class UnitTest11
    {
        [Fact]
        public void Test1()
        {
        }

        [Theory]
        [InlineData("a")]
        [InlineData("с")]
        public void Test2(string str)
        {
        }
    }
}
