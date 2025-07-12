import { ReactElement } from "react";

const NotFoundPage = (): ReactElement => (
    <div className="flex flex-col gap-2 justify-center items-center">
        <h1 className="text-4xl font-bold">404</h1>
        <p className="text-lg">Page not found</p>
    </div>
);

export default NotFoundPage;