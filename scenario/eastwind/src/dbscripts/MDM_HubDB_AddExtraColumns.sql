set quoted_identifier on
GO

/* Set DATEFORMAT so that the date strings are interpreted correctly regardless of
   the default DATEFORMAT on the server.
*/
SET DATEFORMAT mdy
GO
use "Northwind"
go
alter table Products add [RCORGUID] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
       [RCOROWNER] [varchar](500) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
       [RCORLASTMODIFIED] [datetime] NULL,
       [RCORLASTMODIFIEDBY] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
       [RCOREXPIRY] [datetime] NULL,
       [RCORDELETED] [int] NULL,
       [RCOROPERATION] [int] NULL