<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
    <head>
        <title>Hello World!</title>
    </head>
    <body>
     
	
		<h1 th:inline="text">Hello Login Please!! </h1>
        <form th:action="@{/login}" method="post">
            <input type="submit" value="Sign Out"/>
        </form> 
        
		
		
		        
        <form action="/accountCreate" >
			<br />Don't have an account, Create One!!!<br />
			<input type="submit" value="Create Account">
		</form>
    </body>
</html>
